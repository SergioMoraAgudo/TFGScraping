// Call the dataTables jQuery plugin
$(document).ready(function() {
    // Lo dejo vacío
  });
  
async function registroCliente(){
  //se obtiene los datos de los imputs
  let datos={};
  datos.email=document.getElementById('inputEmail').value;
  datos.pass=document.getElementById('inputPassword').value;

  let repetirPassword=document.getElementById('inputPasswordConfirm').value;
  //Si las contraseñas no coinciden se corta la ejecucion de la fucnion
  if (repetirPassword != datos.pass){
    alert('Ambas contraseñas deben coincidir');
    return;
  }
  const response = await fetch('api/cliente', {
    method: 'POST',
    headers: {
      'Accept': 'application/json',
      'Content-Type': 'application/json'
    },
    body:JSON.stringify(datos)
  });
  window.location.href='login.html';
  alert(' Bienvenido ',datos.email,'!!');
}
  
  