function H = hess_dixon_func(x)
	n = 10;
	H = approx_hessian('dixon_func',x,0.001);
end