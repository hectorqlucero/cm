(ns sk.handlers.fotos.handler
  (:require [sk.layout :refer [application]]
            [sk.models.crud :refer [Query db]]
            [sk.models.util :refer [get-session-id]]))

(def fotos-sql
  "
  SELECT
  DATE_FORMAT(fecha, '%W ') as dia,
  DATE_FORMAT(fecha, '%e de %M %Y') as f_fecha,
  enlace
  FROM fotos
  ORDER BY fecha desc")

(defn get-rows []
  (Query db fotos-sql))

(defn get-fotos []
  [:div.container
   [:table.easyui-datagrid {:style "width:100%;height:500px;"
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
      [:th {:data-options "field:'enlace'" :style "text-align:center;"} "PROCESAR"]]]
    [:tbody
     (for [row (get-rows)]
       [:tr
        [:td (:dia row)]
        [:td (:f_fecha row)]
        [:td [:a.btn.btn-info {:href (:enlace row) :target "_blank"} [:span.float-right "Ver Fotos"]]]])]]])

(defn fotos [_]
  (let [title "Fotos - Rodadas"
        ok (get-session-id)
        content (get-fotos)]
    (application title ok nil content)))
