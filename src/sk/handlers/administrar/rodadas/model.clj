(ns sk.handlers.administrar.rodadas.model
  (:require [sk.models.crud :refer [Query db]]
            [sk.models.email :refer [host send-email]]
            [sk.models.util :refer [capitalize-words]]))

(def process-sql
  "SELECT * FROM rodadas WHERE id = ?")

(def process-email-sql
  "SELECT * FROM rodadas_link WHERE rodadas_id = ?")

(defn email-body [row]
  {:from "hectorqlucero@fastmail.com"
   :to (:email row)
   :subject (:titulo row)
   :body [{:type "text/html;charset=utf-8"
           :content (str "<b>Hola</b> "
                         (capitalize-words (:user row)) ",<br><br>"
                         "Se cancela esta rodada. Gracias por su comprensión.<br><br>"
                         "Atte,<br>"
                         (capitalize-words (:leader row)) "<br>"
                         (:leader_email row))}]})

(defn send-mail [row]
  (let [body (email-body row)]
    (send-email host body)))

(defn process-email
  "Mandar correo electronico de cancelacion"
  [rodadas-id]
  (let [row          (first (Query db [process-sql rodadas-id]))
        titulo       (:titulo row)
        leader       (:leader row)
        leader_email (:leader_email row)
        trows        (Query db [process-email-sql rodadas-id])
        rows         (map #(assoc % :titulo titulo :leader leader :leader_email leader_email) trows)]
    (map send-mail rows)))
