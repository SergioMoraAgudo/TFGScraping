$(document).ready(function() {
});

async function iniciarSesion(){
  console.log("Inicio de sesion")
  //Se crea un objeto datos donde almacenar la información del cliente 
  let datos={};
  datos.email=document.getElementById('inputEmail').value;
  datos.pass=document.getElementById('inputPassword').value;

  //se realiza la peticion al back
  const response = await fetch('api/login', {
    method: 'POST',
    headers: {
      'Accept': 'application/json',
      'Content-Type': 'application/json'
    },
    body:JSON.stringify(datos)
  });
  //Mediante el await se espera la respuesta del back y esta será convertida a JSON
  const respuesta = await response.json();
  if (respuesta.success == 'OK') {
    //el correo y el token geneado se guarda en local storage
    localStorage.token=respuesta.token;
    localStorage.email=datos.email;
    window.location.href='misProductos.html';
  }
  else alert(' Credenciales erróneas, intente de nuevo');
}