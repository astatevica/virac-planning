describe('admin dashboard test', () => {

    beforeEach(() => {
        cy.loginAdmin();
        cy.wait(600);
        cy.get('[data-testid="cypress-employee-navigation"]').click();
        cy.wait(600);
    });

    afterEach(() => {
        cy.contains('button', 'Logout').should('be.visible').click();
    
        // Pārbaude ka atgriezās login lapā
        cy.url().should('include', '/login');
      });

    it('Add new employee', () => {

        cy.get('[data-testid="cypress-employee-name"]').type('Test');

        cy.get('[data-testid="cypress-employee-surname"]').type('Test');

        cy.get('[data-testid="cypress-employee-position"]').type('Developer');

        cy.get('select').first().select('Electronics and Satellite Technology');

        cy.get('[data-testid="cypress-employee-add"]').click();

        cy.wait(300);

        cy.contains('Test Test').should('exist');

        cy.contains('Developer').should('exist');
    });

    it('Updates existing employee', () => {

        cy.get('[data-testid="cypress-employee-update"]').first().click();

        cy.get('[data-testid="cypress-employee-name"]').clear().type('UpdatedName');

        cy.get('[data-testid="cypress-employee-surname"]').clear().type('UpdatedSurname');

        cy.get('[data-testid="cypress-employee-position"]').clear().type('Senior Developer');
        
        cy.get('select').first().select('Electronics and Satellite Technology');

        cy.get('[data-testid="cypress-employee-add"]').click();

        cy.contains('UpdatedName UpdatedSurname').should('exist');

        cy.contains('Senior Developer').should('exist');

    });

    it('Deletes existing employee', () => {
        
        cy.get('[data-testid^="employee-row-"]')
        .last()
        .then(($row) => {

            const employeeText = $row.text();

            cy.wrap($row)
                .find('[data-testid="cypress-employee-delete"]')
                .click();

            cy.contains(employeeText)
                .should('not.exist');
        });

    });

    it('Filters employees by department', () => {

        cy.get('[data-testid="cypress-filter-department"]').select('Electronics and Satellite Technology');

        cy.get('[data-testid="cypress-filter-button"]').click();

        // pārbauda ka tādi employee eksistē
        cy.contains('Electronics and Satellite Technology').should('exist');

        //cy.contains('Astronomy and Astrophysics').should('not.exist');

    });

    
})