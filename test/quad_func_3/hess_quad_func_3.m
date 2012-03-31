function H = hess_quad_func_3(x)
	xd = [4; 7; 10];
	H = approx_hessian('quad_func_3',x,0.001);
end