function y = func_for_problem_A_example_16_2_nocedal_wright(x)
	Q = [6 2 1; 2 5 2; 1 2 4];
	q = [-8; -3; -3];
	c = 0;
	y = 0.5*x'*Q*x + q'*x + c;
end