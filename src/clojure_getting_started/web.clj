(ns clojure-getting-started.web
  (:require [compojure.core :refer [defroutes GET PUT POST DELETE ANY]]
            [compojure.handler :refer [site]]
            [compojure.route :as route]
            [clojure.java.io :as io]
            [ring.adapter.jetty :as jetty]
            [environ.core :refer [env]]))

(defn row [a b c d e]
  (apply str
   "<tr>"
   "<td>" a "</td>"
   "<td>" b "</td>"
   "<td>" c "</td>"
   "<td>" d "</td>"
   "<td>" e "</td>"
   "</tr>"
  ))

(defn splash []
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body ; "Hello"
   (apply str
	  "<h1>Backup Matrix</h1>"
	  "<table>"
	  (apply str (repeat 4
		  (row "<select><option>ssh-key</option>
	       <option>login/pass</option></select>"
		       "<input type=text value='158.1.23.55' />"
		       "<select><option>database</option>
	       <option>folder</option></select><input type=text />"
		       "<input type=submit value='Force backup now'/>"
		       "<select><option>2017-04-12 8:00</option>
	       <option>2017-04-12 8:00</option></select><input type=submit value='Recover'/>"
	       )
		  ))
   		 (row "" "" "" "" "<input type=button value='Add new'>")  "</table>")
   })

(defroutes app
  (GET "/" []
       (splash))
  (ANY "*" []
       (route/not-found (slurp (io/resource "404.html")))))

(defn -main [& [port]]
  (let [port (Integer. (or port (env :port) 5000))]
    (jetty/run-jetty (site #'app) {:port port :join? false})))

;; For interactive development:
;; (.stop server)
;; (def server (-main))
