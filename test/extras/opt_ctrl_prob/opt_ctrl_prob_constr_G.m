function G = opt_ctrl_prob_constr_G (N)
	G = zeros(3*N,2*N);
	for k=1:N
		G(3*k-2,2*k-1) = -1;
		G(3*k-1,2*k) = -1;
		G(3*k,2*k) = 1;
	end
end