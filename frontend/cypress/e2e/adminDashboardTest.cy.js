describe('admin dashboard test', () => {

    before(() => {
        cy.loginAdmin();
    });

    it('Updates scheduler dates and saves', () => {

    cy.get('[data-testid="cypress-planned-date"]')
        .clear()
        .type('2026-05-20')
        .should('have.value', '2026-05-20');

    cy.get('[data-testid="cypress-done-date"]')
        .clear()
        .type('2026-12-27')
        .should('have.value', '2026-12-27');

    cy.get('[data-testid="cypress-scheduler-save"]')
        .should('be.enabled')
        .click();

    cy.contains('button', 'Logout').should('be.visible').click();

    });
})