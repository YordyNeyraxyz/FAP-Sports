describe('Página de Login - Deportes FAP', () => {
  beforeEach(() => {
    cy.visit('http://localhost:5500/index.html');
  });

  it('Carga correctamente la página', () => {
    cy.contains('Iniciar Sesión');
    cy.get('input#correo').should('exist');
    cy.get('input#contrasena').should('exist');
    cy.get('button.btn-principal').should('contain', 'Iniciar Sesión');
  });

  it('Valida que no se pueda enviar el formulario vacío', () => {
    cy.get('button.btn-principal').click();
    cy.get('input:invalid').should('have.length.at.least', 1);
  });

  it('Permite ingresar un correo y contraseña válidos', () => {
    cy.get('#correo').type('usuario@fap.com');
    cy.get('#contrasena').type('contrasena123');
    cy.get('button.btn-principal').click();
  });

  it('Verifica que el botón de Google está visible', () => {
    cy.get('button.btn-google').should('be.visible');
    cy.get('button.btn-google img').should('have.attr', 'src').and('include', 'google');
  });

  it('Verifica enlaces adicionales', () => {
    cy.get('.olvide').should('contain', 'Olvidé mi contraseña');
    cy.get('.registro a').should('contain', 'Regístrate');
    cy.get('.inicio').should('contain', 'Página de Inicio');
  });
});
