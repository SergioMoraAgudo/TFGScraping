// al cargar la página ejecuta muestraEmailUsuario()
window.addEventListener('DOMContentLoaded', event =>{
  muestraEmailUsuario();
});
// añade el email del usuario logeado a la barra lateral
function muestraEmailUsuario(){
  document.getElementById("usuarioLogeado").outerHTML=localStorage.email;
}
// termina la sesion iniciada
function logout(){
  localStorage.removeItem('token');
  localStorage.removeItem('email');
  window.location.href='login.html';
}