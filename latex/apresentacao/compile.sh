pdflatex -output-directory bin presentation.tex
bibtex bin/presentation
pdflatex -output-directory bin presentation.tex
gnome-open bin/presentation.pdf
