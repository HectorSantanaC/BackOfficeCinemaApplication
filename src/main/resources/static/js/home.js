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
    // Llamada al endpoint REST que devuelve JSON
    fetch('/api/peliculas')
      .then(function (response) {
        if (!response.ok) throw new Error('Error en la petición: ' + response.status);
        return response.json();
      })
      .then(function (data) {
        if (!Array.isArray(data) || data.length === 0) {
          $("#htmlListaPeliculas").html('<p>No hay películas en el repositorio.</p>');
          return;
        }

        var html = '<div class="table-responsive"><table class="table table-striped">';
        html += '<thead><tr><th>Título</th><th>Sinopsis</th><th>Género</th><th>Director</th><th>Reparto</th><th>Año</th><th>Fecha estreno</th><th>Distribuidor</th><th>País</th></tr></thead>';
        html += '<tbody>';

        data.forEach(function (p) {
          html += '<tr>' +
            '<td>' + (p.titulo || '') + '</td>' +
            '<td>' + (p.synopsis || '') + '</td>' +
            '<td>' + (p.genero || '') + '</td>' +
            '<td>' + (p.director || '') + '</td>' +
            '<td>' + (p.reparto || '') + '</td>' +
            '<td>' + (p.anio || '') + '</td>' +
            '<td>' + (p.fechaEstreno || '') + '</td>' +
            '<td>' + (p.distribuidor || '') + '</td>' +
            '<td>' + (p.pais || '') + '</td>' +
            '</tr>';
        });

        html += '</tbody></table></div>';
        $("#htmlListaPeliculas").html(html);
      })
      .catch(function (err) {
        console.error(err);
        $("#htmlListaPeliculas").html('<p class="text-danger">Error al obtener las películas.</p>');
      });
  });
});
