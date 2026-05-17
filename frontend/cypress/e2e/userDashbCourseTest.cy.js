describe('User Dashboard tests', () => {
  
    beforeEach(() => {
        cy.login();
    });

    afterEach(() => {
        cy.contains('button', 'Logout').should('be.visible').click();
        cy.url().should('include', '/login');
    });

    it.skip('Add new course (create new course)', () => {
        cy.contains('button', 'Add Course').should('exist').click();
        cy.get('[data-testid="course-existing"]').click();
        cy.get('[data-testid="course-new"]').click();
        cy.get('[data-testid="course-name"]').type('Cypress Course');
        cy.get('[data-testid="course-ects"]').type('5');
        cy.get('[data-testid="course-semester"]').type('Spring');
        cy.get('[data-testid="course-faculty"]').type('ITF');
        cy.get('[data-testid="course-work-done"]').type('Initial work done');
        cy.get('[data-testid="course-save-button"]').click();
        // pārbauda ka course parādās planā
        cy.contains('Cypress Course').should('exist');
    });

    it.skip('Shows validation errors when fields are empty', () => {
        cy.contains('button', 'Add Course').should('exist').click();
        cy.get('[data-testid="course-new"]').click();
        cy.get('[data-testid="course-save-button"]').click();
        cy.contains('Name') .should('exist');
        cy.contains('ECTS').should('exist');
        cy.contains('Semester') .should('exist');
        cy.contains('Faculty').should('exist');
        cy.contains('Work done').should('exist');
        cy.get('[data-testid="course-cancel-button"]').click();
    });

    it.skip('Add existing course,autocomplete', () => {
        cy.contains('button', 'Add Course').click();
        cy.get('[data-testid="course-existing"]').click();
        cy.get('[data-testid="course-autocomplete-text"]').type('Math');
        cy.contains('div', 'Math').should('be.visible').click();
        cy.get('[data-testid="course-work-done"]').type('Reviewed lectures');
        cy.get('[data-testid="course-save-button"]').click();
    });

    it('Edits course work done', () => {
        cy.contains('li', 'Cypress Course').within(() => {
            cy.contains('Edit').click();
        });
        cy.get('[data-testid="article-edit-work-done"]').clear().type('Updated work done via Cypress');
        cy.get('[ data-testid="course-edit-save-button"]').click();
        cy.contains('Updated work done via Cypress').should('exist');
    });

    it('Deletes course from plan', () => {
        cy.contains('button', 'Delete Course').should('exist').click();
        cy.contains('Delete Course').should('be.visible');
        cy.get('[data-testid="course-delete-1"]').click();
        cy.on('window:confirm', () => true);
        cy.contains('Cypress Course').should('not.exist');
    });

    it('Cancels course creation', () => {
        cy.contains('button', 'Add Course').click();
        cy.get('[data-testid="course-new"]').click();
        cy.get('[data-testid="course-name"]').type('Should NOT be saved');
        cy.get('[data-testid="course-cancel-button"]').click();
        cy.contains('Should NOT be saved').should('not.exist');
    });

})