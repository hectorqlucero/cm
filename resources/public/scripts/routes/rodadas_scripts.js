$(document).ready(function() {
  $('#calendar').fullCalendar({
    themeSystem: 'bootstrap4',
    eventLimit: true,
    displayEventTime: true,
    timeFormat: 'h(:mm) t',
    header: {
      left: 'prev,next today',
        center: 'title',
        right: ''
    },
    locale: 'es',
    eventClick: function(event, jsEvent, view) {
      $('#modalTitle').html(event.title);
      $('#modalBody').html(event.description);
      $('#eventUrl').attr('href', event.url);
      $('#fullCalModal').modal();
      return false;
    },
    eventMouseover: function(calEvent, jsEvent) {
      var tdesc = calEvent.email || '';
      var tooltip = '<div class="tooltipevent" style="z-index:100001;border;1px solid #F1D031;color:#444;background:#FFFFA3;box-shadow:0 2px 3px #999;position:absolute;padding:5px;text-align:left;border=radius:5px;moz-border-radius:5px;-webkit-border-radius:5px;"><p><strong>Click para ver detalles o confirmar asistencia</p></div>';
      var $tooltip = $(tooltip).appendTo('body');
      $(this).mouseover(function(e) {
        $tooltip.fadeIn('500');
        $tooltip.fadeTo('10', 1.9);
      }).mousemove(function(e) {
        var pLeft;
        var pTop;
        var offset = 10;
        var CursorX = e.pageX;
        var CursorY = e.pageY;
        var WindowWidth = $(window).width();
        var WindowHeight = $(window).height();
        var toolTip = $('.tooltipevent');
        var TTWidth = toolTip.width();
        var TTHeight = toolTip.height();
        if(CursorX-offset >= (WindowWidth/4)*3) {
          pLeft = CursorX - TTWidth - offset;
        } else {
          pLeft = CursorX + offset;
        }
        if(CursorY-offset >= (WindowHeight/4)*3) {
          pTop = CursorY - TTHeight - offset;
        } else {
          pTop = CursorY + offset;
        }
        $tooltip.css('top', pTop);
        $tooltip.css('left', pLeft);
      });
    },
    eventMouseout: function(calEvent, jsEvent) {
      $(this).css('z-index', 8);
      $('.tooltipevent').remove();
    },
    events: '/table_ref/calendar',
  });
});
