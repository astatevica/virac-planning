describe('Student Work tests', () => {

  beforeEach(() => {
    cy.login();
  });

  afterEach(() => {
    cy.contains('button', 'Logout').should('be.visible').click();
    cy.url().should('include', '/login');
  });

  it.skip('Adds new student work successfully', () => {
    cy.contains('button', 'Add Student Work').click();
    cy.get('[data-testid="sw-work-name"]').type('Cypress Thesis');
    cy.get('[data-testid="sw-student-name"]').type('John');
    cy.get('[data-testid="sw-student-surname"]').type('Doe');
    cy.get('[data-testid="sw-degree"]').select('magistrs');
    cy.get('[data-testid="sw-work-done"]').type('Initial research completed');
    cy.get('[data-testid="sw-save-button"]').click();
    cy.contains('Cypress Thesis').should('exist');
  });

  it.skip('Shows validation errors when fields are empty', () => {
    cy.contains('button', 'Add Student Work').click();
    cy.get('[data-testid="sw-save-button"]').click();
    cy.contains('Name').should('exist');
    cy.contains('Student name').should('exist');
    cy.contains('Student surname').should('exist');
    cy.contains('Degree').should('exist');
    cy.contains('Work done').should('exist');
    cy.get('[data-testid="sw-cancel-button"]').click();
  });

  it.skip('Cancels student work creation', () => {
    cy.contains('button', 'Add Student Work').click();
    cy.get('[data-testid="sw-work-name"]').type('Should NOT be saved');
    cy.get('[data-testid="sw-cancel-button"]').click();
    cy.contains('Should NOT be saved').should('not.exist');
  });

  it.skip('Edits student work (work done field only)', () => {
    cy.contains('li', 'Cypress Thesis').within(() => {
        cy.contains('Edit').click();
    });
    cy.get('[data-testid="sw-edit-work-done"]').clear().type('Updated work done via Cypress');
    cy.get('[data-testid="sw-edit-save-button"]').click();
    cy.contains('Updated work done via Cypress').should('exist');
  });

  it.skip('Deletes student work from plan', () => {
    cy.contains('button', 'Delete Student Work').click();
    cy.get('[data-testid^="sw-delete-"]').first().within(() => {
        cy.contains('Delete').click();
    });
    cy.contains('Cypress Thesis').should('not.exist');
  });

});