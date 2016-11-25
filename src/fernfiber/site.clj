(ns fernfiber.site
  (:require [clojure.java.io :as io]
            [clojure.string :as str]
            [hiccup.element :refer [image link-to javascript-tag]]
            [hiccup.page :refer [html5 include-css include-js]]
            [hiccup.util :refer [to-uri]]
            [stasis.core :as stasis]))

(defn spacer [height]
  [:div {:style (format "height: %spx;" height)}])

(defn navbar [site-data]
  [:div.navbar-wrapper
   [:div.container
    [:nav.navbar.navbar-default.navbar-static-top
     {:role "navigation"
      :style "margin-bottom: 0px;"}
     [:div.container
      [:div.navbar-header
       [:button.navbar-toggle.collapsed
        {:type "button"
         :data-toggle "collapse"
         :data-target "#navbar"
         :aria-expanded "false"
         :aria-controls "navbar"}
        [:span.sr-only "Toggle navigation"]
        [:span.icon-bar]
        [:span.icon-bar]
        [:span.icon-bar]]
       (link-to {:class "navbar-brand"} "index.html" #_"http://fernfiber.com"
         (image {:id "navbar-logo"} "img/navbar-logo.png"))]
      [:div#navbar.navbar-collapse.collapse
       [:ul.nav.navbar-nav
        (for [{:keys [attributes url text target]} (:nav site-data)]
          [:li attributes
           [:a {:href (to-uri url) :target target} text]])]]]]]])

(defn slideshow [site-data]
  [:div {:style "min-height: 50px;"}
   [:div#slider1_container {:style "display: none; position: relative; margin: 0 auto; top: 0px; left: 0px; width: 1300px; height: 500px; overflow: hidden;"}
    [:div {:u "loading"
           :style "position: absolute; top: 0px; left: 0px;"}
     [:div {:style "filter: alpha(opacity=70); opacity: 0.7; position: absolute; display: block; top: 0px; left: 0px; width: 100%; height: 100%;"}]
     [:div {:style "position: absolute; display: block; background: url(img/loading.gif) no-repeat center center; top: 0px; left: 0px; width: 100%; height: 100%;"}]]
    [:div {:u "slides"
           :style "cursor: move; position: absolute; left: 0px; top: 0px; width: 1300px; height: 500px; overflow: hidden;"}
     (for [img (:index-images site-data)]
       [:div [:img {:u "image" :src2 img}]])
     [:div {:u "any"
            :style "position: absolute; display: block; bottom: 25px; right: 25px; width: 280px; height: 40px;"}
      (for [{:keys [name url title]} (:sharing site-data)]
        [:a {:class (str "share-icon share-" name)
             :target "_blank"
             :title title
             :href url}])]]
    [:div.jssorb21 {:u "navigator"
                    :style "position: absolute; bottom: 26px; left: 6px;"}
     [:div {:u "prototype"
            :style "POSITION: absolute; WIDTH: 19px; HEIGHT: 19px; text-align:center; line-height:19px; color:White; font-size:12px;"}]]
    [:span.jssora21l {:u "arrowleft"
                      :style "width: 55px; height: 55px; top: 123px; left: 8px;"}]
    [:span.jssora21r {:u "arrowright"
                      :style "width: 55px; height: 55px; top: 123px; right: 8px;"}]
    (link-to {:style "display: none;"} "http://www.jssor.com" "Image Slider")]])

(defn signup-form [decline?]
  [:form.validate ;;.form-horizontal
   {:action "http://fernfiber.us10.list-manage.com/subscribe/post?u=527190f152e3871cea7c8a657&amp;id=67db298ab1"
    :method "post"
    :name "mc-embedded-subscribe-form"
    :target "_blank"
    :novalidate ""}
   [:div.form-group
    [:input.form-control
     {:type "email"
      :name "EMAIL"
      :value ""
      :placeholder "email address"
      :required ""}]]
   [:div {:style "position: absolute; left: -5000px;"}
    [:input {:type "text"
             :name "b_527190f152e3871cea7c8a657_67db298ab1"
             :tabindex "-1"
             :value ""}]]
   [:input.subscribe-button.btn.btn-primary
    {:type "submit"
     :value "Subscribe"
     :name "subscribe"}]
   (when decline?
     [:a.subscribe-decline
      "No thanks"])])

(def enticement
  [:div#enticement.boxaloo
   [:h3 "Welcome!"]
   (image {:class "close-x subscribe-decline"} "img/x.png")   
   [:p "Sign up for our Fern Fiber Email Newsletter for a free winter tea recipe. Stay up to date with new yarns, coupons, free patterns, tutorials, and more:"]
   (signup-form true)])

(defn page [site-data & body]
  (html5
    [:head
     [:meta {:charset "utf-8"}]
     [:meta {:http-equiv "X-UA-Compatible"
             :content "IE=edge"}]
     [:meta {:name "viewport"
             :content "width=device-width, initial-scale=1.0"}]
     [:title "Fern Fiber"]
     (include-css
       "css/bootstrap.min.css"
       "css/site.css")]
    [:body
     (navbar site-data)
     body
     enticement
     [:div.container
      [:footer
       [:p.pull-left "Fern logo by "
        (link-to "http://www.constancelombardo.com/" "Constance Lombardo")]
       [:p.pull-right "&copy; Fern Fiber 2015-2016"]]]
     (include-js
       "js/jquery-3.1.1.min.js"
       "js/bootstrap.min.js"
       "js/docs.min.js"
       "js/ie10-viewport-bug-workaround.js"
       "js/jssor.slider.mini.js"
       "js/site.js")
     (javascript-tag "(function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
      (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
      m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
      })(window,document,'script','//www.google-analytics.com/analytics.js','ga');

      ga('create', 'UA-59490706-1', 'auto');
      ga('send', 'pageview');")]))

(defn index-page [site-data]
  (page site-data
    (slideshow site-data)
    [:div.container.marketing
     (spacer 20)
     [:div.row.featurette
      [:div.col-md-12
       (image {:class "pull-left logo-inline"} "img/logo.png")
       [:div#mc_embed_signup.pull-right.boxaloo
        [:p "Join our mailing list for shop updates and our newsletter:"]
        (signup-form false)]
       [:p.lead
        "At Fern Fiber we believe in small farms and sustainable fiber. We strive to dye wool that is ecologically raised and processed without the use of harsh chemicals. We support our local community and work closely with fiber and flower farmers, mushroom gatherers, and local companies of the Appalachian Mountains. If we are not gathering materials ourselves from our local forests and gardens, we are getting to know the people who are."]
       [:p.lead
        "In the majestic mountains we call home, ferns grow abundantly in the forests and along sparkling woodland streams. In spring, as the ferns unfurl, tiny new green growth appears. These new leaves represent rebirth, abundance, and connection to the earth. As we gather local herbs, flowers, mushrooms, and barks we think of this earth connection and the way it is portrayed and painted in our yarns. We began our journey as gardeners and herbalists, aligned to the changing seasons and cycles of nature. We delight in using this knowledge of the natural world in our fiber and art."]]]
     (spacer 20)
     [:div.row
      [:div.col-md-12
       [:iframe
        {:src "https://snapwidget.com/embed/code/213774"
         :title "Instagram Widget"
         :class "snapwidget-widget"
         :allowTransparency "true"
         :frameborder "0"
         :scrolling "no"
         :style "border:none; overflow:hidden; width: 100%; height:150px"}]]]]))

(defn yarn-metric [yarn metric]
  (let [name (str/capitalize (name metric))]
    [:div.metric {:class name}
     [:span.name name ": "]
     [:span (metric yarn)]]))

(defn yarn-page [site-data]
  (page site-data
    [:div.header-img
     (image "img/mtn-crop.png")]
    [:div.container.marketing
     [:div.row.featurette
      [:div.col-md-12
       [:h1#yarn-header "Our Yarns"]
       [:h2 "Blue Ridge Mountains Local Yarn"]
       [:p
        "Our focus is on yarns grown, spun, and dyed in the Blue Ridge Mountains that we call home.  Our local yarns change by the animal, farmer, and season.  We get to know the farmers in our area and purchase fleece directly from them, often taking part in the shearing.  We have these fleeces spun at one of the family mills in our mountains and then generally dye these yarns with our locally grown and harvested plant dyes.  These yarns are unique!  They carry with them a grand story of their creation from fleece and farmer to their final recipient.  You not only get to know a bit about the dye plants that were used on your yarns, and the farms where the fleeces came from, but often you will even know the name of the sheep or alpaca that produced your yarn."]
       [:p
        "Our beautiful local yarns are not always available.  When the shearing is done and the yarns have been dyed and sold, we may have to wait until the next shearing season for more fleece. So, we also carry 5 commercial bases in our store. These bases, in line with our belief in living a gentler, more sustainable life, are all non-superwash and gently produced."]

       [:h2 "Non-Local Yarn"]
       (for [{:keys [name weight description] :as yarn} (:yarns site-data)]
         [:div.yarn
          [:h3 name " - " weight]
          [:p.description description]
          (map (partial yarn-metric yarn) [:blend :volume :needle :gauge])])
       [:h2 "Handspun Yarn"]
       [:p
        "Along with dyeing, we also love to spin yarn. Occasionally you can find some of our handspun yarn in <a href=\"http://fernfiber.etsy.com/\">our store</a>."]]]]
    
    (spacer 20)))

(defn faq-page [site-data]
  (page site-data
    [:div.header-img
     (image "img/mtn-crop.png")]
    [:div.container.marketing
     [:div.row.featurette
      [:div.col-md-12
       [:h1#yarn-header "Frequently Asked Questions"]
       (for [[question answer] (:faqs site-data)]
         [:div.faq
          [:h3 question]
          [:p.answer answer]])]]]
    (spacer 20)))

(defn designs-page [site-data]
  (page site-data
    [:div.header-img
     (image "img/mtn-crop.png")]
    [:div.container.marketing
     [:div.row.featurette
      [:div.col-md-12
       [:h1#yarn-header "Knitwear Designs"]
       [:h3
        "We love to see new designs created with Fern Fiber yarn. Here you'll find some of those lovely designs as well as creations by the Fern Fiber ladies."]
       (spacer 10)
       (for [row (partition 2 2 nil (:designs site-data))]
         [:div
          [:div.row
           (for [{:keys [name designer url img description]} row]
             [:div.col-md-6.design
              [:p (link-to url (image (str "img/" img)))]
              [:h4 (link-to url name)]
              [:p "By " designer]
              [:p description]])]
          (spacer 50)])]]]
    (spacer 20)))

(defn get-pages [site-data]
  {"/index.html" (index-page site-data)
   "/our-yarns.html" (yarn-page site-data)
   "/faq.html" (faq-page site-data)
   "/designs.html" (designs-page site-data)})

(defn copy-assets [resource-dir export-dir regex]
  (let [resource-dir' (io/file resource-dir)
        base-len (inc (.length (.getAbsolutePath resource-dir')))]
    (doseq [f (file-seq resource-dir')
            :when (re-find regex (.getName f))
            :let [dest-path (.substring (.getAbsolutePath f) base-len)
                  dest-file (io/file export-dir dest-path)]]
      (.mkdirs (.getParentFile dest-file))
      (io/copy f dest-file))))

(def export-dir "dist")

(defn export []
  (stasis/empty-directory! export-dir)
  (copy-assets "resources" export-dir #"\.(css|js|jpg|png)$")
  (let [site-data (read-string (slurp (io/resource "site-data.edn")))]
    (stasis/export-pages (get-pages site-data) export-dir)))
