describe('User Dashboard tests', () => {
  
  beforeEach(() => {

    cy.login();
  });

  afterEach(() => {
    cy.contains('button', 'Logout').should('be.visible').click();

    // Pārbaude ka atgriezās login lapā
    cy.url().should('include', '/login');
  });

  it("opens plan and downloads DOCX", () => {
    cy.get('[data-testid="cypress-title-dashboard"]').should("include.text", "2026");

    // open plan
    cy.get('[data-testid="cypress-open"]').click();
    cy.url().should("include", "/user/full-plan/");

    // back to dashboard
    cy.go("back");

    // download DOCX 
    //TODO: atkomentēt (aizkomentēju, lai nesūta visu laiku epastus un nespamo)
    // cy.get('[data-testid="cypress-docx"]').click();
    // cy.get('[data-testid="cypress-docx"]').should("exist");

    //View All Plans Button
    cy.get('[data-testid="cypress-view-all-plans"]').click();
    cy.url().should("include", "/user/plans");
    cy.go("back");
  });

  it('Open and cancel project creation', () => {
    cy.contains('button', 'Add Project').should('exist').click();

    cy.contains('Add Project To Plan').should('be.visible');

    cy.get('[data-testid="cypress-project-search"]').type('Pro');
    cy.wait(300);

    // Wait for autocomplete results
    cy.contains('div', 'Project 1').should('be.visible').click();

    cy.get('[data-testid="cypress-tasks-project"]').type('Create Cypress automation tests');

    cy.get('[data-testid="cypress-work-done-plan"]').type('Implemented modal tests');

    cy.get('[data-testid="cypress-cancel-project"]').click();
    cy.wait(300);
    cy.contains('Add Project To Plan').should('not.exist');

    cy.contains('Project 1').should('not.exist');
  });


  it('Creates new project', () => {
    cy.contains('button', 'Add Project').click();
    cy.contains('Add Project To Plan').should('be.visible');
    cy.get('[data-testid="cypress-project-search"]').type('Pro');
    cy.contains('div', 'Project 1').should('be.visible').click();
    cy.get('[data-testid="cypress-tasks-project"]').type('Testing project creation');
    cy.get('[data-testid="cypress-work-done-plan"]').type('Created Cypress E2E tests');
    cy.get('[data-testid="cypress-save-project"]').should('be.enabled').click();
    cy.contains('Add Project To Plan').should('not.exist');
    cy.contains('Project 1').should('exist');
  });
  
  it('Edits existing project', () => {
    cy.get('[data-testid="cypress-edit-project-1"]').click();
    cy.contains('Edit Project').should('be.visible');
    cy.get('[data-testid="cypress-edit-project-tasks"]').clear().type('Testing project edit');
    cy.get('[data-testid="cypress-edit-project-work-done"]').clear().type('Testing project edit');
    cy.get('[data-testid="cypress-edit-project-save"]').click();
    cy.contains('Project 1').should('exist');
  });

  it('Delete existing project', () => {
    cy.contains('button', 'Delete Project').should('exist').click();
    cy.contains('Delete Project From Plan').should('be.visible');
    cy.get('[data-testid="cypress-delete-project-1"]').click();
    cy.on('window:confirm', () => true);
    cy.contains('Project 1').should('not.exist');
  });

  

})