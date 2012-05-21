function H = hess_huang_aggerwal_func(x)
	H = approx_hessian('huang_aggerwal_func',x,0.00001);
end