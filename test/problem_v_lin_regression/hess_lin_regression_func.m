function H = hess_lin_regression_func(x)
	eta = [-0.5; -2; -3; -3; -2.5; -2; -1; 1; 3; 5.5];
	xi = [1:1:10]';
	H = approx_hessian('lin_regression_func',x,0.00001);
end