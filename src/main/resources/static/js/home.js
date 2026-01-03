//Se sobrecarga el botón toggle
$(function () {
  // Sidebar toggle behavior
  $("#sidebarCollapse").on("click", function () {
    $("#sidebar, #content").toggleClass("active");
  });
});

//Se sobrecarga botón de Guardar de ventana modal de Nueva Película y
//evento onclick de Listar Películas
$(document).ready(function () {
  // Mostrar Mi Perfil al hacer click
  $("#btnMiPerfil").on("click", function (e) {
    e.preventDefault(); // evita que el anchor recargue la página
    $("#seccion01").show(); // muestra la sección Mi Perfil
  });

  /////////////////////////////////////////////////////////////////////////////
  //Aquí deberías implementar la llamada asincrona al servidor para enviar por
  //método POST los datos de la nueva película.
  $("body").on("click", "#GuardarPelicula", function () {
    //En esta zona debería ubicarse la llamada asíncrona al servidor enviando los datos de la nueva película

    $("#NuevaPeliculaCenter").modal("hide"); //Se oculta la ventana modal
    $("body").removeClass("modal-open"); //Eliminamos la clase del body para poder hacer scroll
    $(".modal-backdrop").remove(); //eliminamos el backdrop del modal

    //Si se puedo guardar una nueva película entonces mostrar la lista de peliculas:
    $("#ListarPeliculasVisual").modal("show");
  });

  ///////////////////////////////////////////////////////////////////////
  //Aquí debería implementar la llamada asíncrona para obtener los datos de las películas
  //y cargarlos dentro de la capa #htmlListaPeliculas
  $("body").on("click", "#IdListarPeliculas", function () {
	
	$.ajax({
	        url: '/listarPeliculas', 
	        dataType: "json",
	        method: 'GET',
	        success: function(respuesta){
				
	            if (respuesta.error) {
	                $('#htmlListaPeliculas').html('<p>Error: '+respuesta.msgError+'</p>');
	            } else {
	                let html = "<table class='table table-striped table-bordered'>";
	                html += "<thead><tr>";
	                html += "<th>Título</th><th>Synopsis</th><th>Género</th>";
	                html += "<th>Director</th><th>Reparto</th><th>Año</th>";
	                html += "<th>Fecha estreno</th><th>Distribuidor</th><th>País</th>";
	                html += "</tr></thead><tbody>";
	                
	                respuesta.peliculas.forEach(function(pelicula) {
	                    html += "<tr>";
	                    html += "<td>" + pelicula.titulo + "</td>";
	                    html += "<td>" + pelicula.synopsis + "</td>";
	                    html += "<td>" + pelicula.genero + "</td>";
	                    html += "<td>" + pelicula.director + "</td>";
	                    html += "<td>" + pelicula.reparto + "</td>";
	                    html += "<td>" + pelicula.anio + "</td>";
	                    html += "<td>" + pelicula.fechaEstreno + "</td>";
	                    html += "<td>" + pelicula.distribuidor + "</td>";
	                    html += "<td>" + pelicula.pais + "</td>";
	                    html += "</tr>";
	                });
	                
	                html += "</tbody></table>";
	                $('#htmlListaPeliculas').html(html);
	                
	                $("#ListarPeliculasVisual").modal("show");
	            }
	        },
	        error: function(){
	            $('#htmlListaPeliculas').html('<p>Error Fatal al cargar películas</p>');
	        }
		});
	});
});
