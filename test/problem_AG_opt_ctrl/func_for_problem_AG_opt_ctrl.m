function y = func_for_problem_AG_opt_ctrl(x)
	Q = opt_ctrl_prob_func_H(10^(-3),1,10);
	q = opt_ctrl_prob_func_q(10);
	c = 0;
	y = 0.5*x'*Q*x + q'*x + c;
end