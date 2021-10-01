(ns sk.models.util
  (:require [clj-jwt.core :as j]
            [clj-time.coerce :as c]
            [clj-time.core :as t]
            [clojure.java.io :as io]
            [clojure.string :as string]
            [noir.session :as session]
            [sk.models.crud :refer [Query config db]])
  (:import java.util.UUID))

(def tz (t/time-zone-for-id (:tz config)))

(defn fix-id [v]
  (if (clojure.string/blank? v) nil v))

(defn current_year
  "GEt simple date formatted year"
  []
  (t/year (t/from-time-zone (t/now) tz)))

(defn get-month-name [month]
  (cond
    (= month 1)  "Enero"
    (= month 2)  "Febrero"
    (= month 3)  "Marzo"
    (= month 4)  "Abril"
    (= month 5)  "Mayo"
    (= month 6)  "Junio"
    (= month 7)  "Julio"
    (= month 8)  "Agosto 4"
    (= month 9)  "Septiembre"
    (= month 10) "Octubre"
    (= month 11) "Noviembre"
    (= month 12) "Diciembre"))

(defn parse-int
  "Attempt to convert to integer or on error return nil or itself if it's already an integer"
  [s]
  (try
    (Integer. s)
    (catch Exception _ (if (integer? s) s nil))))

(defn get-session-id []
  (try
    (if (session/get :user_id) (session/get :user_id) 0)
    (catch Exception e (.getMessage e))))

(defn user-level []
  (let [id   (get-session-id)
        type (if (nil? id)
               nil
               (:level (first (Query db ["select level from users where id = ?" id]))))]
    type))

(defn user-email []
  (let [id    (get-session-id)
        email (if (nil? id)
                nil
                (:username (first (Query db ["select username from users where id = ?" id]))))]
    email))

(defn capitalize-words
  "Captitalizar todas las palabras en una hilera"
  [s]
  (->> (string/split (str s) #"\b")
       (map string/capitalize)
       (string/join)))

(defn zpl
  "n=number,c=zeropad number"
  [n c]
  (loop [s (str n)]
    (if (< (.length s) c)
      (recur (str "0" s))
      s)))

;; Start jwt token
(defn get-base-url [request]
  (str  "https://" (:server-name request) ":" 443))

(defn get-reset-url [request token]
  (str (get-base-url request) "/reset_password/" token))

(defn create-token
  "Creates jwt token with 10 minutes expiration time"
  [k]
  (let [data {:iss k
              :exp (t/plus (t/now) (t/minutes 10))
              :iat (t/now)}]
    (-> data j/jwt j/to-str)))

(defn decode-token
  "Decodes jwt token"
  [t]
  (-> t j/str->jwt :claims))

(defn verify-token
  "Verifies that token is good"
  [t]
  (-> t j/str->jwt j/verify))

(defn check-token
  "Checks if token verifes and it's not expired, returns id or nil"
  [t]
  (let [token    (decode-token t)
        exp      (:exp token)
        cexp     (c/to-epoch (t/now))
        token-id (:iss token)]
    (if (and (verify-token t) (> exp cexp))
      token-id
      nil)))
;; End jwt token

;; Start image stuff
(defn build-img-html [img-val uuid path]
  (if (or
       (nil? img-val)
       (nil? uuid))
    nil
    (str "<img src='" path  img-val "?t=" uuid "' onError=\"this.src='/images/placeholder_profile.png'\" width='95' height='71'></img>")))

(defn get-img-val [table-name field-name id-name id-value]
  (if (or
       (nil? table-name)
       (nil? field-name)
       (nil? id-name)
       (nil? id-value))
    nil
    ((keyword field-name) (first (Query db (str "SELECT " field-name " FROM " table-name " WHERE " id-name " = " id-value))))))

(defn get-image
  "Get an image from a table and specify extra folder 
   ex. (get-image 'eventos' 'imagen' 'id' 6 "
  [table-name field-name id-name id-value & extra-folder]
  (let [img-val     (get-img-val table-name field-name id-name id-value)
        uuid        (str (UUID/randomUUID))
        path        (str (:path config) (first extra-folder))
        placeholder "<img src='/images/placeholder_profile.png' width='95' height='71'>"
        image       (build-img-html img-val uuid path)]
    (if (empty? img-val) placeholder image)))

(defn upload-image
  "Uploads image and renames it to the id passed"
  [file id path]
  (let [tempfile   (:tempfile file)
        size       (:size file)
        type       (:content-type file)
        extension  (peek (clojure.string/split type #"\/"))
        extension  (if (= extension "jpeg") "jpg" "jpg")
        image-name (str id "." extension)
        result     (if-not (zero? size)
                     (io/copy tempfile (io/file (str path image-name))))]
    (if result image-name nil)))
;; End image stuff

;; Start hiccup stuff
(defn build-image-field []
  (list
   [:input {:id "imagen" :name "imagen" :type "hidden"}]
   [:div {:style "margin-bottom:10px;width:100%;max-width:400px;"}
    [:div {:style "width:99%;max-width:398px;display:flex;overflow:none;vertical-align:middle;"}
     [:div {:style "float:left;margin-right:2px;"}
      [:img#image1 {:width  "95"
                    :height "71"
                    :style  "margin-right:2px;cursor:pointer;"}]]
     [:div {:style "float:right;margin-left:2px;vertical-align:middle;"}
      [:input {:id           "file"
               :name         "file"
               :class        "easyui-filebox"
               :style        "width:300px;"
               :data-options "prompt:'Escoge imagen...',
                              buttonText:'Escoge imagen...',
                              onChange: function(value) {
                                var f = $(this).next().find('input[type=file]')[0];
                                if (f.files && f.files[0]) {
                                  var reader = new FileReader();
                                  reader.onload = function(e) {
                                    $('#image1').attr('src', e.target.result);
                                  }
                                  reader.readAsDataURL(f.files[0]);
                                }
                              }"}]]]]))

(defn build-image-field-script []
  (str
   "
    $('#image1').click(function() {
      var img = $('#image1');
      if(img.width() < 500) {
        img.animate({width: '500', height: '500'}, 1000);
      } else {
        img.animate({width: img.attr(\"width\"), height: img.attr(\"height\")}, 1000);
      }
    });
    "))

(defn build-toolbar [& extra]
  [:div#toolbar
   [:a {:href         "javascript:void(0)"
        :class        "easyui-linkbutton"
        :data-options "iconCls: 'icon-add',plain: true"
        :onclick      "newItem()"} "Crear"]
   [:a {:href         "javascript:void(0)"
        :class        "easyui-linkbutton"
        :data-options "iconCls: 'icon-edit',plain: true"
        :onclick      "editItem({})"} "Editar"]
   [:a {:href         "javascript:void(0)"
        :class        "easyui-linkbutton"
        :data-options "iconCls: 'icon-remove',plain: true"
        :onclick      "deleteItem()"} "Remover"]
   extra
   [:div {:style "float: right"}]])

(defn build-dialog-buttons []
  [:div#dlg-buttons
   [:a {:href         "javascript:void(0)"
        :class        "easyui-linkbutton c6"
        :style        "margin-right:5px;"
        :data-options "iconCls: 'icon-ok'"
        :onclick      "saveItem()"} "Postear"]
   [:a {:href         "javascript:void(0)"
        :class        "easyui-linkbutton"
        :data-options "iconCls: 'icon-cancel'"
        :onclick      "dialogClose()"} "Cancelar"]])

(defn build-table [title url fields]
  [:table.dg
   {:style "width: 100%;height:500px;"
    :title title
    :data-options
    (str
     "
     url: '" url "',
     toolbar: '#toolbar',
     queryParams: {'__anti-forgery-token':token},
     pagination: false,
     rownumbers: true,
     nowrap: false,
     autoRowHeight: true,
     fitColumns: true,
     autoSizeColumns: true,
     singleSelect: true")}
   [:thead
    [:tr
     fields]]])

(defn build-field [options]
  [:div {:style "margin-bottom:10px;"}
   [:input options]])

(defn build-text-editor
  "ex. {:label 'My Label' :name 'fieldname' :placeholder 'myplaceholder' :class 'easyui-textbox'}"
  [options]
  [:div {:style "margin-bottom:10px;"}
   [:label (:label options)]
   [:textarea (dissoc options :label)]])

(defn build-button [options]
  [:div {:style "text-align:center;padding:5px 0"}
   (if (list? options)
     (for [option options]
       [:a option])
     [:a options])])

(defn build-radio-buttons
  "Builds radio button fields options has list of build-field options"
  [label options]
  [:div.form-group.col-10
   [:label [:span label]]
   (for [option options]
     [:div {:style "margin-bottom:5px;"} [:input option]])])

(defn build-form [title token fields buttons & options]
  [:div.easyui-panel {:style        "width:100%;
                              max-width:600px;
                              padding:30px 60px;"
                      :title        title
                      :data-options "style:{margin:'0 auto'}"}
   [:form.fm (or
              (first options)
              {:method       "post"
               :enctype      "multipart/form-data"
               :data-options "novalidate:true"})
    token
    fields]
   buttons
   (if-not (nil? options) (first options))])

(defn build-dialog [title fields & options]
  [:div.dlg.easyui-dialog {:closed  "true"
                           :buttons "#dlg-buttons"
                           :style   "width:100%;padding:10px 20px;max-width:600px;"}
   [:div#p.easyui-panel {:title title
                         :style "width:100%;
                                 max-width:600px;
                                 height:auto;
                                 max-height:98%;
                                 padding:10px 20px"}
    [:form.fm (or (first options) {:method "post"
                                   :enctype "multipart/form-data"
                                   :data-options "novalidate:true"})
     fields]]])
;; End hiccup stuff
