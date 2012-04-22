function H = hess_bazaraa_shetty_func(x)
	H = [ 12*(x(1)-2)^2+2  -4;
               -4           8 ];
end