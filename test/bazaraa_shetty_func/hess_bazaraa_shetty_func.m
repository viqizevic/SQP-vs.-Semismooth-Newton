function H = hess_bazaraa_shetty_func(x)
	H = approx_hessian('bazaraa_shetty_func',x,0.001);
end