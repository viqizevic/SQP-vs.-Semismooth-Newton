## Funktion: [x, it] = semismooth_newton(f, gradf, hessf, lamda, a, b, x0, itmax, tol)
##
##  Semismooth-Newton-Verfahren loest das Problem
##        min ( f(x) + (lambda/2)*|x|^2 )
##         x
##        mit Nebenbedingung a <= x <= b
##
##  f : R^n -> R
##  lambda eine reelle Zahl
##  a und b aus R^n

function [x, it] = semismooth_newton(f, gradf, hessf, lambda, a, b, x0, itmax, tol)

	x = x0;
	it = 0;
	stop = false;
	
	## Ueberpruefe das Abbruchkriterium fuer x0
	if( norm(feval(gradf,x)) < tol )
		stop = true;
	endif

	while( ~stop )
		it = it + 1;
		d = feval(hessf,x) \ -feval(gradf,x);
		%sigma = armijo_schrittweite(f, gradf, x, d, 0.75, 4, 0.5);
		sigma = powell_schrittweite(f, gradf, x, d, 0.01, 0.9);
		x = x + sigma*d;
		% Stop criteria
		if( norm(feval(gradf,x)) < tol )
			stop = true;
		endif

		% Too many iteration
		if( it >= itmax)
			stop = true;
		endif
	endwhile

endfunction


function w = projection(v, a, b)
	## TODO: ueberpruefe ob a <= b gilt
	w = min(b,max(a,v));
endfunction
