# coding: utf-8
lib = File.expand_path('../lib', __FILE__)
$LOAD_PATH.unshift(lib) unless $LOAD_PATH.include?(lib)
require 'lib/version'
require 'rake'

Gem::Specification.new do |spec|
  spec.name = "math_tool"
  spec.version = MathTool::VERSION
  spec.author = 'Martin Prout'
  spec.email = "martin_p@lineone.net"
  spec.description = <<-EOS
  This MathTool proves as a ruby-module certain math methods used in vanilla processing.
  EOS
  spec.summary = %q{A MathTool for ruby-processing.}
  spec.homepage = "https://github.com/ruby-processing/MathTool"
  spec.license = 'MIT'

  spec.files = FileList['lib/**/*', 'library/**/*'].exclude(/jar/).to_a
  spec.files << 'lib/rpextras.jar'

  spec.test_files    = spec.files.grep(%r{^(test|spec|features)/})
  spec.require_paths = ["lib"]
  spec.required_ruby_version = '>= 2.1'

  spec.add_development_dependency "rake", "~> 10.4"
  spec.add_development_dependency "mvn"
  spec.add_development_dependency "rspec", "~> 3.3"
end
