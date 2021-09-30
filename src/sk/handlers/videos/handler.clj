(ns sk.handlers.videos.handler
  (:require [hiccup.page :refer [html5]]
            [sk.layout :refer [application]]
            [sk.models.crud :refer [Query db]]
            [sk.models.util :refer [get-session-id]]))

(def videos-sql
  "
  SELECT
  DATE_FORMAT(fecha, '%W ') as dia,
  DATE_FORMAT(fecha, '%e de %M %Y') as f_fecha,
  titulo,
  enlace
  FROM videos
  ORDER BY fecha desc")

(defn get-rows []
  (Query db videos-sql))

(defn get-videos [title]
  [:div.container
   [:table.easyui-datagrid {:style "width:100%;height:500px;"
                            :title title
                            :data-options "pagination:false,
                                          remoteFilter:false,
                                          remoteSort:false,
                                          rownumbers:true,
                                          nowrap:true,
                                          resizeEdge:5,
                                          autoRowHeight:true,
                                          fitColumns:true,
                                          autoSizeColumns:true,
                                          singleSelect:true"}
    [:thead
     [:tr
      [:th {:data-options "field:'dia'"} "DIA"]
      [:th {:data-options "field:'f_fecha'"} "FECHA"]
      [:th {:data-options "field:'titulo'"} "TITULO"]
      [:th {:data-options "field:'enlace'" :style "text-align:center;"} "PROCESAR"]]]
    [:tbody
     (for [row (get-rows)]
       [:tr
        [:td (:dia row)]
        [:td (:f_fecha row)]
        [:td (:titulo row)]
        [:td [:a.btn.btn-info {:href (:enlace row) :target "_blank"} [:span.float-right "Ver Videos"]]]])]]])

(defn videos [_]
  (let [title "Videos - Ciclismo Mexicali"
        ok (get-session-id)
        content (get-videos title)]
    (application title ok nil content)))
