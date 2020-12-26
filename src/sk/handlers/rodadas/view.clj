(ns sk.handlers.rodadas.view
  (:require [hiccup.page :refer [include-css include-js]]
            [sk.models.util :refer [build-form
                                    build-field
                                    build-radio-buttons
                                    build-button]]))

(defn line-rr [label value]
  [:div.row
   [:div.col-xs-4.col-sm-4.col-md-3.col-lg-1.text-primary [:strong label]]
   [:div.col-xs.8.col-sm-8.col-md-9.col-lg-11 value]])

(defn body-rr [row]
  (list
    [:h2 (:titulo row)
     [:div.card
      [:div.card-body {:style "font-size:.5em;"}
       (line-rr "Fecha:" [:strong.text-warning (str (clojure.string/upper-case (:dia row)) (clojure.string/upper-case (:f_fecha row)))])
       (line-rr "Detalles: " (:detalles row))
       (line-rr "Punto de reunion: " (:punto_reunion row))
       (line-rr "Hora: " (:salida row))
       (line-rr "Distancia: " (:distancia row))
       (line-rr "Velocidad: " (:velocidad row))
       (line-rr "Lider: " (:leader row))
       (line-rr "Lider Email: " (:leader_email row))
       [:div.card-action
        [:a {:href (str "/rodadas/asistir/" (:id row))
             :target "_blank"} [:strong.text-warning "Clic para confirmar asistencia"]]]]]]))

(defn rr-view [rows]
  (map body-rr rows))

(defn rodadas-view []
  (list
   (include-css "/font/css/all.min.css")
   (include-css "/fullcalendar/fullcalendar.min.css")
   [:div#calendar]
   [:div#fullCalModal.modal.fade
    [:div.modal-dialog
     [:div.modal-content
      [:div.modal-header
       [:h4#modalTitle.model-title]
       [:button.close {:type         "button"
                       :data-dismiss "modal"}
        [:span {:aria-hidden "true"} "x"]
        [:span.sr-only "Cerrar"]]]
      [:div#modalBody.modal-body]
      [:div.modal-footer
       [:button.btn.btn-default {:type         "button"
                                 :data-dismiss "modal"} "Regresar al Calendario"]
       [:a#eventUrl.btn.btn-primary {:target "_blank"} "Confirmar Asistencia"]]]]]))

(defn rr-scripts []
  [:script])

(defn rodadas-scripts []
  (list
    (include-js "/font/js/all.min.js")
    (include-js "/fullcalendar/lib/moment.min.js")
    (include-js "/fullcalendar/fullcalendar.min.js")
    (include-js "/fullcalendar/locale-all.js")
    [:script
     "
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
          var tooltip = '<div class=\"tooltipevent\" style=\"z-index:100001;border;1px solid #F1D031;color:#444;background:#FFFFA3;box-shadow:0 2px 3px #999;position:absolute;padding:5px;text-align:left;border=radius:5px;moz-border-radius:5px;-webkit-border-radius:5px;\"><p><strong>Click para ver detalles o confirmar asistencia</p></div>';
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
     "]
    ))

(defn asistir-view [title rodadas_id token]
  (build-form
    title
    token
    (list
      (build-field
        {:id "rodadas_id"
         :name "rodadas_id"
         :type "hidden"
         :value (str rodadas_id)})
      (build-field
        {:id "user"
         :name "user"
         :class "easyui-textbox"
         :data-options "label:'Nombre:',labelPosition:'top',
                       required:true,width:'100%'"})
      (build-field
        {:id "comentarios"
         :name "comentarios"
         :class "easyui-textbox"
         :data-options "label:'Comentarios:',labelPosition:'top',
                       width:'100%',height:'120px',required:true,
                       multiline:true"})
      (build-field
        {:id "email"
         :name "email"
         :class "easyui-textbox easyui-validatebox"
         :validType "email"
         :data-options "label:'Email:',labelPosition:'top',
                       width:'100%',required:true"})
      (build-radio-buttons
        "Asistire?"
        (list
          {:id "asistir_si"
           :name "asistir"
           :class "easyui-radiobutton"
           :value "T"
           :label "Si"
           :data-options "checked:true"}
          {:id "asistir_no"
           :name "asistir"
           :class "easyui-radiobutton"
           :label "No"
           :value "F"
           :data-options "checked:false"})))
    (list
      (build-button
        (list
          {:text "Postear"
           :id "submitbtn"
           :class "easyui-linkbutton c6"
           :style "margin-right:5px;margin-bottom:5px;"
           :data-options "iconCls:'icon-ok'"
           :href "javascript:void(0)"
           :onclick "saveData()"}
          {:text "Regresar"
           :id "regresar"
           :class "easyui-linkbutton"
           :style "margin-bottom:5px;"
           :data-options "iconCls:'icon-back'"
           :href "javascript:void(0)"
           :onclick "goBack()"})))))

(defn asistir-scripts []
  [:script
   "
    var token = $('__anti-forgery-token').val();

    function goBack() {
        window.close();
    }

    function saveData() {
        $('.fm').form('submit', {
            url: '/rodadas/asistir',
            queryParams: {'__anti-forgery-token': token},
            onSubmit: function() {
              if($(this).form('validate')) {
                $('#submitbtn').linkbutton('disable');
                $('#submitbtn').linkbutton({text: 'Processando!'});
              }
              return $(this).form('enableValidation').form('validate');
            },
            success: function(result) {
                var json = JSON.parse(result);
                if(json.error && json.success) {
                    $.messager.show({
                        title: 'Error',
                        msg: json.success + '<br>' + json.error
                    });
                } else if (json.error) {
                    $.messager.show({
                        title: 'Error',
                        msg: json.error
                    });
                } else if (json.success) {
                    $.messager.show({
                        title: 'Success',
                        msg: json.success
                    });
                    window.close();
                }
            }
        });
    }
   "])
