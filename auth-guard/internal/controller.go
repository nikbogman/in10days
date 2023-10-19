package internal

import (
	"encoding/json"
	"log"
	"net/http"

	"github.com/clerkinc/clerk-sdk-go/clerk"
)

type Controller struct {
	client clerk.Client
}

func NewController(client clerk.Client) *Controller {
	return &Controller{client: client}
}

func (c *Controller) Authorize() http.HandlerFunc {
	return func(w http.ResponseWriter, r *http.Request) {
		w.WriteHeader(http.StatusOK)
		w.Write([]byte("Authorized"))
	}
}

func (c *Controller) GetUserDetails() http.HandlerFunc {
	return func(w http.ResponseWriter, r *http.Request) {
		ctx := r.Context()
		session, ok := ctx.Value(clerk.ActiveSessionClaims).(*clerk.SessionClaims)
		if !ok {
			http.Error(w, "Unauthorized", http.StatusUnauthorized)
			return
		}
		user, err := c.client.Users().Read(session.Claims.Subject)
		if err != nil {
			http.Error(w, "Internal server error", http.StatusInternalServerError)
			log.Printf("Error fethcing user details: %v", err)
			return
		}
		w.Header().Set("Content-Type", "application/json")
		w.WriteHeader(http.StatusOK)
		json.NewEncoder(w).Encode(user)
	}
}
