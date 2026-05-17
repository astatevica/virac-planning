describe('User Dashboard tests', () => {
  
    beforeEach(() => {
        cy.login();
    });

    afterEach(() => {
        cy.contains('button', 'Logout').should('be.visible').click();
        cy.url().should('include', '/login');
    });

    it('Creates new article with new journal', () => {
        cy.contains('button', 'Add Article').should('exist').click();
        cy.get('[data-testid="article-create-new-article"]').click();

        cy.get('[data-testid="article-name"]').type('Cypress Article 1');

        cy.get('[data-testid="article-coauthors"]').type('John Doe');

        cy.get('[data-testid="article-new-journal-name"]').type('Cypress Journal');

        cy.get('[data-testid="article-new-journal-create"]').click();

        cy.contains('created').should('be.visible');

        cy.get('[data-testid="article-comments"]').type('Test comment');

        cy.get('[data-testid="article-publication-link"]').type('https://test.com');

        cy.get('[data-testid="article-save-button"]').click();

        cy.contains('Cypress Article 1').should('exist');
    });

    it('Adds existing article', () => {
        cy.contains('button', 'Add Article').should('exist').click();
        cy.get('[data-testid="article-use-existing-article"]').click();

        cy.get('[data-testid="article-autocomplete-input"]').type('det');

        // autocomplete izvēle
        cy.get('div').contains('det').should('be.visible').click();

        cy.get('[data-testid="article-comments"]').type('Existing article comment');

        cy.get('[data-testid="article-publication-link"]').type('https://existing.com');

        cy.get('[data-testid="article-save-button"]').click();
    });

    it('Edits article', () => {

        cy.contains('li', 'Cypress Article 1').within(() => {
            cy.contains('Edit').click();
        });

        cy.get('[data-testid="article-edit-comments"]').clear().type('Updated comment');

        cy.get('[data-testid="article-edit-publication-link"]').clear().type('https://updated.com');

        cy.get('[data-testid="article-edit-save-button"]').click();

        cy.contains('Updated comment').should('exist');
    });

    it('Cancels article creation', () => {

        cy.contains('button', 'Add Article').should('exist').click();

        cy.get('[data-testid="article-create-new-article"]').click();

        cy.get('[data-testid="article-name"]').type('Should not be saved');

        cy.get('[data-testid="article-cancel-button"]').click();

        cy.contains('Should not be saved').should('not.exist');
    });

    it('Deletes article', () => {
        cy.contains('button', 'Delete Article').should('exist').click();
        cy.contains('Delete Article From Plan').should('be.visible');
        cy.get('[data-testid="article-delete-1"]').click();
        cy.on('window:confirm', () => true);
        cy.contains('Cypress Article 1').should('not.exist');
    });

    it('Add text for plan', () =>{
        cy.get('[name="partInConfEnd"]').type("Test participation in conf end");
        cy.contains('button', 'Save changes').should('exist').click();
        cy.contains('Test participation in conf end').should('exist');
    })

  

})