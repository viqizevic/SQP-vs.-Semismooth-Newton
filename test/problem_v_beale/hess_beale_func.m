function H = hess_beale_func(x)
	H = approx_hessian('beale_func',x,0.00001);
end