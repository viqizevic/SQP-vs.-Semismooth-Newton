function b = opt_ctrl_prob_constr_b (phi0, w0, N)
	% Bilde Vektor g := [ phi0, w0, 0, ..., 0 ]^T aus R^(2N+2)
	b = zeros(2*N+2,1);
	b(1) = phi0;
	b(2) = w0;
end