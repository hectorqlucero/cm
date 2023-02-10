(ns sk.layout
  (:require [clj-time.core :as t]
            [hiccup.page :refer [html5 include-css include-js]]
            [sk.models.util :refer [user-level user-name]]
            [sk.models.crud :refer [Query db]]
            [sk.migrations :refer [config]]))

(defn build-menu-item [title url]
  [:li {:data-options "plain:true"} [:span [:a.easyui-linkbutton {:href url
                                                                  :data-options "plain:true"} title]]])

(defn build-aventuras []
  (let [rows (Query db "select * from cmt order by nombre")]
    (list
     (map (fn [row]
            (build-menu-item (row :nombre) (str "/aventuras/" (row :id)))) rows))))

(defn build-admin []
  (list
   (build-menu-item "Rodadas" "/admin/rodadas")
   (build-menu-item "Aventuras" "/admin/aventuras")
   (when (or
          (= (user-level) "A")
          (= (user-level) "S"))
     (list
      (build-menu-item "CMT Cicloturismo" "/admin/cmt")
      (build-menu-item "Eventos" "/admin/eventos")
      (build-menu-item "Fotos" "/admin/fotos")
      (build-menu-item "Videos" "/admin/videos")
      (build-menu-item "Frases de Ciclistas" "/admin/frases")
      (build-menu-item "Talleres" "/admin/talleres")
      (build-menu-item "Grupos" "/admin/cuadrantes")))
   (when (= (user-level) "S")
     (build-menu-item "Usuarios" "/admin/users"))))

(defn menus-private []
  (list
   [:ul#tt.easyui-tree
    [:li
     [:span "Aventuras"]
     [:ul
      (build-aventuras)]]
    (build-menu-item "Eventos" "/eventos/list")
    (build-menu-item "Rodadas" "/rodadas/list")
    (build-menu-item "Fotos" "/fotos/list")
    (build-menu-item "Videos" "/videos/list")
    (build-menu-item "Frases de Ciclistas" "/frases/list")
    (build-menu-item "Talleres" "/talleres/list")
    (build-menu-item "Grupos" "/grupos/list")
    (when
     (or
      (= (user-level) "U")
      (= (user-level) "A")
      (= (user-level) "S"))
      (do
        (list
         [:li
          [:span "Administrar"]
          [:ul
           (build-admin)]])))
    (build-menu-item (str "Salir [" (user-name) "]") "/home/logoff")]))

(defn menus-public []
  (list
   [:ul#tt.easyui-tree
    [:li
     [:span "Cicloturismo"]
     [:ul
      (build-aventuras)]]
    (build-menu-item "Eventos" "/eventos/list")
    (build-menu-item "Rodadas" "/rodadas/list")
    (build-menu-item "Fotos" "/fotos/list")
    (build-menu-item "Videos" "/videos/list")
    (build-menu-item "Frases de Ciclistas" "/frases/list")
    (build-menu-item "Talleres" "/talleres/list")
    (build-menu-item "Grupos" "/grupos/list")
    (build-menu-item "Entrar al sitio" "/home/login")]))

(defn menus-none []
  nil)

(defn app-css []
  (list
   (include-css "/bootstrap/css/bootstrap.min.css")
   (include-css "/bootstrap/css/lumen.min.css")
   (include-css "https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css")
   (include-css "/bxslider/dist/jquery.bxslider.min.css")
   (include-css "/easyui/themes/material-blue/easyui.css")
   (include-css "/css/main.css")
   (include-css "/RichText/src/richtext.min.css")))

(defn app-js []
  (list
   (include-js "/easyui/jquery.min.js")
   (include-js "/popper/popper.min.js")
   (include-js "/bxslider/dist/jquery.bxslider.min.js")
   (include-js "/bootstrap/js/bootstrap.min.js")
   (include-js "/easyui/jquery.easyui.min.js")
   (include-js "/easyui/jquery.edatagrid.js")
   (include-js "/easyui/datagrid-detailview.js")
   (include-js "/easyui/datagrid-groupview.js")
   (include-js "/easyui/datagrid-bufferview.js")
   (include-js "/easyui/datagrid-scrollview.js")
   (include-js "/easyui/datagrid-filter.js")
   (include-js "/easyui/locale/easyui-lang-es.js")
   (include-js "/RichText/src/jquery.richtext.min.js")
   (include-js "/js/main.js")))

(defn application [title ok js & content]
  (html5
   {:ng-app (config :site-name) :lang "es"}
   [:head
    [:title (if title title (config :site-name))]
    [:meta {:charset "UTF-8"}]
    [:meta {:name "viewport"
            :content "width=device-width, initial-scale=1"}]
    (app-css)
    [:link {:rel "shortcut icon"
            :type "image/x-icon"
            :href "data:image/x-icon;,"}]]
   [:body.box {:style "padding:20px;"}
    [:div.easyui-layout {:data-options "fit:true"}
     [:div {:data-options "region:'north'"}
      [:img {:href "/images/logo.png"}]
      [:a.easyui-linkbutton {:href "/" :data-options "plain:true"} "Ciclismo Mexicali. Aventuras sobre ruedas e información para el ciclista."]]
     [:div {:data-options "region:'west'"
            :style "width:20%;padding:5px;"}
      (cond
        (= ok -1) (menus-none)
        (= ok 0) (menus-public)
        (> ok 0) (menus-private))]
     [:div {:data-options "region:'center'"} content]]
    [:div {:data-options "region:'south'"
           :style "text-align:center;"} (str "Copyright @" (t/year (t/now)) " Lucero Systems - All Rights Reserved")]
    (app-js)
    js]))

(defn error-404 [content return-url]
  (html5 {:ng-app (:site-name config) :lang "es"}
         [:head
          [:title "Mesaje"]
          [:meta {:charset "UTF-8"}]
          [:meta {:name "viewport"
                  :content "width=device-width, initial-scale=1"}]
          (app-css)
          [:link {:rel "shortcut iconcompojure"
                  :type "image/x-icon"
                  :href "data:image/x-icon;,"}]]
         [:body {:style "width:100vw;height:98vh;border:1px solid #000;"}
          [:div.container {:style "height:88vh;margin-top:75px;"}
           (menus-none)
           [:div.easyui-panel {:data-options "fit:true,border:false" :style "padding-left:14px;"}
            [:div
             [:p [:h3 [:b "Mensaje: "]] content]
             [:p [:h3 [:a {:href return-url} "Clic aqui para " [:strong "Continuar"]]]]]]]

          (app-js)
          nil]
         [:footer.bg-secondary.text-center.fixed-bottom
          [:span  "Copyright &copy" (t/year (t/now)) " Lucero Systems - All Rights Reserved"]]))
