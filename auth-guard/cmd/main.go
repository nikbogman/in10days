package main

import (
	"authguard/internal"
	"log"
	"net/http"

	"github.com/clerkinc/clerk-sdk-go/clerk"
	"github.com/go-chi/chi/v5"
	"github.com/go-chi/chi/v5/middleware"
)

func main() {
	client, err := clerk.NewClient("sk_test_Yi0ZxvPWofBFSf9TbnfLe1Vv1mMLYUldhNgEkh33DB")
	if err != nil {
		log.Panic(err)
	}
	c := internal.NewController(client)

	r := chi.NewRouter()
	r.Use(middleware.Logger)
	r.Handle("/authorize", clerk.RequireSessionV2(client)(c.Authorize()))
	r.Handle("/details", clerk.WithSessionV2(client)(c.GetUserDetails()))

	log.Printf(">> Server is now listening on port: %s%d%s", "\033[33m", 4000, "\033[0m")
	log.Fatal(http.ListenAndServe(":4000", r))
}
