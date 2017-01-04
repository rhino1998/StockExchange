@echo off
setlocal enableextensions enabledelayedexpansion

set /a count = -3

set str = ""
for %%a in (%*) do (
   set /a count += 1
   if !count! geq 0 set str=!str!#%%a
   
)

java -jar %1/target/docs--%2.jar !str! > %1/build/doc.dot
%1/build/graphvis/bin/dot -Tpng < %1/build/doc.dot > %1/docs/class_diagram.png