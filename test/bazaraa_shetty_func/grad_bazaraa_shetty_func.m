function g = grad_bazaraa_shetty_func(x)
	g = [ 4*(x(1)-2)^3+2*x(1)-4*x(2);
	         -4*x(1)+8*x(2) ];
end