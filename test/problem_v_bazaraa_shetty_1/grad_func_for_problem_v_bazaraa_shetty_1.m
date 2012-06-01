function g = grad_func_for_problem_v_bazaraa_shetty_1(x)
	g = [ 4*(x(1)-2)^3+2*x(1)-4*x(2);
	         -4*x(1)+8*x(2) ];
end