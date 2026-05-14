describe("Login tests", () => {
  it("logs in with valid credentials", () => {
    cy.visit("http://localhost:3000/login");

    cy.get('[data-testid="cypress-email"]').type("rimants.k@venta.lv");
    cy.get('[data-testid="cypress-password"]').type("rimants123");

    cy.get('[data-testid="cypress-submit"]').click();
  });
});  

describe("Login tests", () => {
  it("logs in with wrong credentials", () => {
    cy.visit("http://localhost:3000/login");

    cy.get('[data-testid="cypress-email"]').type("wrong@mail.com");
    cy.get('[data-testid="cypress-password"]').type("wrongpass");

    cy.get('[data-testid="cypress-submit"]').click();

    cy.contains("Invalid credentials").should("be.visible");
  });
});