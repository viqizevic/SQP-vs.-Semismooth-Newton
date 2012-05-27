function G = opt_ctrl_prob_constr_G (N)
	G = zeros(2*N,2*N+2);
	for i=1:N
		j = 2*i-1;
		G(j:j+1,2*N+2+i) = [-1; 1];
	end
end