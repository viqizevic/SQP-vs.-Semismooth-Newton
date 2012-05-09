function g = grad_quad_regression_func(x)
	eta = [-0.5; -2; -3; -3; -2.5; -2; -1; 1; 3; 5.5];
	xi = [1:1:10]';
	g = approx_gradient('quad_regression_func',x,0.001);
end