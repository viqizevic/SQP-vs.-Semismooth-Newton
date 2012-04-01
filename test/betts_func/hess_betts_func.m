function H = hess_betts_func(x)
	H = approx_hessian('betts_func',x,0.001);
end