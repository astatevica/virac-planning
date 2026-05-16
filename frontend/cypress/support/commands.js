// ***********************************************
// This example commands.js shows you how to
// create various custom commands and overwrite
// existing commands.
//
// For more comprehensive examples of custom
// commands please read more here:
// https://on.cypress.io/custom-commands
// ***********************************************
//
//
// -- This is a parent command --
//Cypress.Commands.add('login', (email, password) => { 
Cypress.Commands.add('login', () => { 
    cy.visit("http://localhost:3000/login");

    cy.get('[data-testid="cypress-email"]').type("rimants.k@venta.lv");
    cy.get('[data-testid="cypress-password"]').type("rimants123");

    cy.get('[data-testid="cypress-submit"]').click();
 })
//Admin login
Cypress.Commands.add('loginAdmin', () => { 
    cy.visit("http://localhost:3000/login");

    cy.get('[data-testid="cypress-email"]').type("admin.a@venta.lv");
    cy.get('[data-testid="cypress-password"]').type("admin123");

    cy.get('[data-testid="cypress-submit"]').click();
 })
//
// -- This is a child command --
// Cypress.Commands.add('drag', { prevSubject: 'element'}, (subject, options) => { ... })
//
//
// -- This is a dual command --
// Cypress.Commands.add('dismiss', { prevSubject: 'optional'}, (subject, options) => { ... })
//
//
// -- This will overwrite an existing command --
// Cypress.Commands.overwrite('visit', (originalFn, url, options) => { ... })