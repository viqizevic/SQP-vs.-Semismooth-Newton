function g = grad_schuldt_func(x)
	g = [ -(4/100000)*x(1)*(x(2)-x(1)^2);
	      1 + (2/100000)*(x(2)-x(1)^2) ];
end