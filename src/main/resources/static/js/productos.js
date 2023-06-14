window.addEventListener('DOMContentLoaded', event =>{
    compruebaAutenticado();
    listarBusquedas();
  });
// Si no se ha logeado le redirige a la pagina de login
  function compruebaAutenticado(){
    if (localStorage.token == null){
        window.location.href = 'login.html'
    }
  }
// Se obtiene los productos y se meten en el select option
  async function listarBusquedas(){
        
        email=localStorage.email;
        console.log(localStorage.token)
        const response = await fetch(`api/busquedas/${email}`, {
        method: 'GET',
        headers: getHeaders(),
        });
        const productosBuscados = await response.json();
        console.log(productosBuscados)
        let opcionesHTML;
        for (let producto of productosBuscados){
        let id=producto.id.idproducto;
        let nombre=producto.idproducto.nombre;
        opcionesHTML+= `<option onclick="crearTablaProductos()" value="${id}">${nombre}</option>`;
        }
    
        document.getElementById('productosBuscados').innerHTML+=opcionesHTML;
  }

// escribe la tabla donde se muestran los resultados obtenidos de la busqueda por el robot
  async function crearTablaProductos(){
    let idProducto = document.getElementById('productosBuscados').value;
    const response = await fetch(`api/scraping/${idProducto}`, {
        method: 'GET',
        headers: getHeaders()
        });
        const listaProductos = await response.json();
        let productosListados;
        for (let producto of listaProductos){
        productosListados += `
                    <tr>
                        <td>${producto.nombreproducto}</td>
                        <td>${producto.precio}</td>
                        <td><a href="${producto.id.url}">${producto.tiendanombre}</a></td>
                        <td>${producto.valoraciones}</td>
                        <td>${producto.envio}</td>
                    </tr>    
                    `
        }        
      document.getElementsByTagName('tbody')[0].innerHTML=productosListados.split("undefined")[1];
  }


  //Encabezados generales
  function getHeaders(){
    return {
        'Accept': 'application/json',
        'Content-Type': 'application/json',
        'Authorization':localStorage.token  
    };
  }
// El usuario realiza una busqueda 
  async function buscarProducto(){
    let email=localStorage.email;
    let producto = document.getElementById('productoBuscado').value
    let url = `/api/busqueda/${email}/${producto}`
    const response = await fetch(url, {
      method: 'GET',
      headers: getHeaders()
    });
    listarBusquedas();
  }
// El usuario da de baja una busqueda
  async function bajaProducto(){
    let email=localStorage.email;
    let idProducto = document.getElementById('productosBuscados').value;
    const response = await fetch(`api/busqueda/${email}`, {
        method: 'POST',
        headers: getHeaders(),
        body:JSON.stringify(idProducto)
    });
  }
