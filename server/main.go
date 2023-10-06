package main

import (
	"net/http"

	"github.com/clerkinc/clerk-sdk-go/clerk"
	"github.com/labstack/echo/v4"
	"github.com/labstack/echo/v4/middleware"
)

// BindSession middleware sets http.Request.Context ActiveSessionClaims value to echo.Context
func BindSession(next echo.HandlerFunc) echo.HandlerFunc {
	return func(c echo.Context) error {
		sessClaims, ok := c.Request().Context().Value(clerk.ActiveSessionClaims).(*clerk.SessionClaims)
		if !ok {
			return c.String(http.StatusUnauthorized, "Unauthorized")
		}
		c.Set("session", sessClaims)
		return next(c)
	}
}
func main() {
	e := echo.New()

	// Middleware
	e.Use(middleware.Logger())
	e.Use(middleware.Recover())

	// Initialize Clerk client and middleware
	client, err := clerk.NewClient("sk_test_Yi0ZxvPWofBFSf9TbnfLe1Vv1mMLYUldhNgEkh33DB")
	if err != nil {
		panic(err)
	}
	injectActiveSession := clerk.WithSessionV2(client)

	e.GET("/protected", func(c echo.Context) error {
		session := c.Get("session").(*clerk.SessionClaims)
		user, err := client.Users().Read(session.Claims.Subject)
		if err != nil {
			return err
		}
		return c.String(http.StatusOK, "Welcome "+*user.FirstName)
	}, echo.WrapMiddleware(injectActiveSession), BindSession)

	// Start server
	e.Logger.Fatal(e.Start(":1323"))
}
