describe('admin dashboard test', () => {

    before(() => {
        cy.loginAdmin();
        cy.visit("http://localhost:3000/admin/employee");
    });

    
})