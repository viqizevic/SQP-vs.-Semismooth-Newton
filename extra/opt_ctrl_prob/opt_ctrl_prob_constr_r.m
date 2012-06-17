function r = opt_ctrl_prob_constr_r (eta, N)
	r = zeros(3*N,1);
	for k=1:N
		r(3*k,1) = eta;
	end
end