(ns sk.handlers.aventuras.view
  (:require [clojure.string :refer [upper-case]]
            [ring.util.anti-forgery :refer [anti-forgery-field]]
            [sk.models.crud :refer [Query db]]
            [sk.handlers.aventuras.model :refer [get-aventuras-comments]]
            [sk.migrations :refer [config]]))

(defn get-imagen [row]
  (if-not (nil? (:imagen row))
    (str (:path config) (:imagen row) "?" (.toString (java.util.UUID/randomUUID)))
    (str "/images/placeholder_profile.png")))

(defn line-rr [label value]
  [:div.row
   [:div.col-xs-4.col-sm-4.col-md-3.col-lg-2.text-primary [:strong label]]
   [:div.col-xs.8.col-sm-8.col-md-9.col-lg-10 value]])

(defn line-cc [aventuras_id]
  (map (fn [i]
         (let [label [:span [:strong (if (empty? (:nombre i)) "Anonimo" (:nombre i))]]
               valor [:span (:comments i)]]
           (line-rr label valor))) (get-aventuras-comments aventuras_id)))

(defn body-rr [row]
  (let [imagen (get-imagen row)
        the-id (row :id)]
    [:div.container.border.border-dark.rounded {:style " margin-bottom:10px;"}
     [:h2.card-title [:img.rounded-circle {:id (str "img" (:id row))
                                           :style "height:65px;width:65px;margin-top:5px;margin-right:16px"
                                           :src imagen
                                           :width 95
                                           :height 71}] (:nombre row)]
     (line-rr "Fecha:" [:strong.text-warning (str (upper-case (:dia row)) (upper-case (:f_fecha row)))])
     (when (:enlace row)
       (line-rr "Fotos:" [:a.btn.btn-secondary
                          {:href (:enlace row)
                           :data-options "plain:true"
                           :target "_blank"} [:strong.text-secondary "Clic aqui para ver fotos!"]]))
     (when (:enlacev row)
       (line-rr "Videos:" [:a.btn.btn-secondary
                           {:href (:enlacev row)
                            :data-options "plain:true"
                            :target "_blank"} [:strong.text-secondary "Clic aqui para ver videos!"]]))
     (line-rr "Aventura:" (:aventura row)) [:br]
     (line-cc the-id) [:br]
     [:div
      (anti-forgery-field)
      [:form
       [:input {:id (str "autor_" the-id)
                :name "autor"
                :placeholder "autor"
                :size "20"}]
       [:input {:id (str "comment_" the-id)
                :name "comment"
                :placeholder "comentario"
                :size "100"}]
       [:button {:type "button"
                 :id "submit_comment"
                 :onclick (str "process_comment(" the-id ",this.form.autor.value,this.form.comment.value)")} "Enviar Comentario"]]] [:br]]))

(defn aventuras-view [rows crow]
  (list
   [:div.row
    [:div.col [:h4.strong {:style "font-style:italic;
                                  margin:10px;
                                  padding:2px;
                                  color:black;
                                  text-align: justify;
                                  text-justify: inter-word;"} (:comments crow)]]]
   [:div.row
    [:div.col
     [:div.card
      (map body-rr rows)]]]))

(defn aventuras-scripts []
  [:script
   "
   function process_comment(the_id,autor,comment) {
    the_autor = '#autor_'+the_id;
    the_comment = '#comment_'+the_id;
    var token = $('#__anti-forgery-token').val();
    var reload_on_return = true;
    $('#submit_comment').prop('disabled',true);
    $.ajax({
      type: 'POST',
      url: '/aventuras/comentarios',
      data: {'__anti-forgery-token': token, 'aventuras_id': the_id,'nombre': autor,'comments': comment}
    })
    .done(function(msg) {
      var json = JSON.parse(msg);
      if(json.error) {
        $.messager.show({
          title:'Error',
          msg: json.error
        });
      } else {
        $.messager.show({
          title:'Exito',
          msg: json.success
        });
      }
      $(the_autor).val('');
      $(the_comment).val('');
      if (reload_on_return) {
         setTimeout(
           function() 
           {
             location.reload();
           }, 1000);    
      }
    });
    $('#submit_comment').prop('enabled',true);
   }
   "])

(comment
  (aventuras-view (get-rows 4) (get-cmt-rows 387)))
