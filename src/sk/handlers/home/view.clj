(ns sk.handlers.home.view
  (:require [sk.models.util :refer [build-button build-field build-form]]
            [hiccup.core :refer [html]]
            [sk.models.crud :refer [Query db]]))

(def rows
  [{:enlace "https://i.postimg.cc/pd8vQ4qd/121683918-646848542656353-671358903740689963-n.jpg" :first 1}
   {:enlace "https://i.postimg.cc/C1NFN42p/198295209-780213085986564-5322431553199886686-n.jpg" :first 0}
   {:enlace "https://i.postimg.cc/htZBDGK0/20221210-023817-001.jpg" :first 0}
   {:enlace "https://i.postimg.cc/R0HhvVD4/20220422-155351-001.jpg" :first 0}
   {:enlace "https://i.postimg.cc/Px6y6Yfh/224959754-803962946944911-2146294710154552999-n.jpg" :first 0}
   {:enlace "https://i.postimg.cc/kgnwq9Gw/20221211-080315.jpg" :first 0}
   {:enlace "https://i.postimg.cc/qvgqJcVQ/142792882-708851959789344-8030927536000749094-n.jpg" :first 0}
   {:enlace "https://i.postimg.cc/X7DX7k7h/20221211-100528-001.jpg" :first 0}
   {:enlace "https://i.postimg.cc/528mZ4nM/20220516-090445.jpg" :first 0}
   {:enlace "https://i.postimg.cc/85dPKtMk/144094398-708851786456028-5060036999942917149-n.jpg" :first 0}
   {:enlace "https://i.postimg.cc/fTcZJNj0/243346870-843440882997117-8396634743915358586-n.jpg" :first 0}
   {:enlace "https://i.postimg.cc/Fz1Zq0nP/20220516-090521.jpg" :first 0}
   {:enlace "https://i.postimg.cc/SRkV9rsZ/244683695-845108026163736-4958286840517387149-n.jpg" :first 0}
   {:enlace "https://i.postimg.cc/XqMSYHr8/243904773-843441172997088-1666726767559827889-n.jpg" :frist 0}
   {:enlace "https://i.postimg.cc/0QVhySqd/140286251-704467433561130-5532196733978207436-n.jpg" :first 0}
   {:enlace "https://i.postimg.cc/GpkGRDHd/220865108-803384840336055-8784380828312262992-n.jpg" :first 0}
   {:enlace "https://i.postimg.cc/DyqJHTxb/244349180-845830279424844-4476803244705102141-n.jpg" :first 0}
   {:enlace "https://i.postimg.cc/hGNM3h37/20220520-100532.jpg" :fist 0}
   {:enlace "https://i.postimg.cc/tCsVMKw3/244819364-845112159496656-8581272887967347616-n.jpg" :first 0}
   {:enlace "https://i.postimg.cc/k5nqwNkW/20220525-074625-001.jpg" :first 0}
   {:enlace "https://i.postimg.cc/hvR41JWZ/245045634-846587812682424-588526382785745137-n.jpg" :first 0}
   {:enlace "https://i.postimg.cc/pr2fwYHB/20220524-074221.jpg" :first 0}
   {:enlace "https://i.postimg.cc/gJxDgjhR/20221116-065239-001.jpg" :first 0}
   {:enlace "https://i.postimg.cc/LXgjLT7F/20220421-094700.jpg" :first 0}])

(defn slideshow-body [row]
  (list
   (if (= (:first row) 1)
     [:div.carousel-item.active [:img.d-block.w100 {:src (:enlace row) :alt "CM"}]]
     [:div.carousel-item [:img.d-block.w100 {:src (:enlace row) :alt "CM"}]])))

(defn build-slideshow []
  (list
   [:div.carousel.slide {:id "cm"
                         :data-ride "carousel"}
    [:div.carousel-inner
     (map slideshow-body rows)]]))

(defn contact-view []
  (html
   [:div.container
    (build-slideshow)]))

(defn contact-view1 []
  (list
   [:div.container {:style "margin-top:20px;"}
    (build-slideshow)
    [:h2 "Contáctanos"]
    [:form
     [:div.form-group
      [:label {:for "Nombre"} "Nombre"]
      [:input.form-control {:id "nombre"
                            :name "nombre"
                            :required "required"}]]
     [:div.form-group
      [:label {:for "Email"} "Email"]
      [:input.form-control {:id "email"
                            :name "email"
                            :type "email"
                            :required "true"}]]
     [:div.form-group
      [:label {:for "Mensaje"} "Mensaje"]
      [:textarea.form-control {:id "mensaje"
                               :name "mensaje"
                               :rows 5
                               :required "required"}]]
     [:button.btn.btn-primary
      [:i.fa.fa-paper-plane]
      "Mandar Mensaje"]]]))

(defn login-view [token]
  (build-form
   "Conectar"
   token
   (list
    (build-field
     {:id "username"
      :name "username"
      :class "easyui-textbox"
      :prompt "Email aqui..."
      :validType "email"
      :data-options "label:'Email:',labelPosition:'top',required:true,width:'100%'"})
    (build-field
     {:id "password"
      :name "password"
      :class "easyui-passwordbox"
      :prompt "Contraseña aqui..."
      :data-options "label:'Contraseña:',labelPosition:'top',required:true,width:'100%'"})
    (build-button
     {:href "javascript:void(0)"
      :id "submit"
      :text "Acceder al sitio"
      :class "easyui-linkbutton c6"
      :onClick "submitForm()"}))
   (list
    [:div {:style "margin-bottom:10px;"}
     [:a {:href "/register"} "Clic para registrarse"]]
    [:div {:style "margin-bottom:10px;"}
     [:a {:href "/rpaswd"} "Clic para resetear su contraseña"]])))

(defn login-script []
  [:script
   "
    function submitForm() {
        $('.fm').form('submit', {
            onSubmit:function() {
                if($(this).form('validate')) {
                  $('a#submit').linkbutton('disable');
                  $('a#submit').linkbutton({text: 'Processando!'});
                }
                return $(this).form('enableValidation').form('validate');
            },
            success: function(data) {
                try {
                    var dta = JSON.parse(data);
                    if(dta.hasOwnProperty('url')) {
                        window.location.href = dta.url;
                    } else if(dta.hasOwnProperty('error')) {
                        $.messager.show({
                            title: 'Error: ',
                            msg: dta.error
                        });
                        $('a#submit').linkbutton('enable');
                        $('a#submit').linkbutton({text: 'Acceder al sitio'});
                    }
                } catch(e) {
                    console.error('Invalid JSON');
                }
            }
        });
    }
   "])
