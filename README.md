# FieldArithmetics Add-on for Vaadin 7

FieldArithmetics is an extension add-on that evaluates simple calculations on the client-side before the field value is sent to the server side. in addition to the basic operations: +, -, /, *. (plus, minus, division, multiplication) it also has limited support for ^ (power operator), parathesis and locale (thousand and decimal separator) auto guessing.

## Online demo

Try the add-on demo at http://jonni.jelastic.servint.net/FieldArtihmetics/ or see the add-on in action https://www.youtube.com/watch?v=jT4D19h2nd8

## Download release

Official releases of this add-on are available at Vaadin Directory. For Maven instructions, download and reviews, go to https://vaadin.com/addon/fieldarithmetics-add-on

## Building and running demo

git clone git@github.com:Wnt/Vaadin-Field-arithmetics.git
mvn clean install
cd fieldarithmetics-demo
mvn jetty:run

To see the demo, navigate to http://localhost:8080/
