describe('User Dashboard tests', () => {
  it("opens plan and downloads DOCX", () => {
    cy.login();
    cy.get('[data-testid="cypress-title-dashboard"]').should("include.text", "2026");

    // OPEN plan
    cy.get('[data-testid="cypress-open"]').click();
    cy.url().should("include", "/user/full-plan/");

    // back uz dashboard
    cy.go("back");

    // DOWNLOAD DOCX
    cy.get('[data-testid="cypress-docx"]').click();
    cy.get('[data-testid="cypress-docx"]').should("exist");

    // ja tev ir intercept:
    // cy.wait("@download");
  });

})