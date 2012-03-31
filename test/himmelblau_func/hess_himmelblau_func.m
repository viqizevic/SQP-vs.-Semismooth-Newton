function H = hess_himmelblau_func(x)
	H = approx_hessian('himmelblau_func',x,0.001);
end