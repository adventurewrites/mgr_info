REPORT = report
BIBLIO = references

TEX=pdflatex
BIBTEX=bibtex
BUILDTEX=$(TEX) $(REPORT).tex

all:
	$(BUILDTEX)
	$(BIBTEX) $(REPORT)
	$(BUILDTEX)
	$(BUILDTEX)

clean:
	rm -f report.pdf *.bbl *.blg *.log *.lof *.lot *.aux *.toc *.dvi *.nav *.out *.snm

.PHONY: all
