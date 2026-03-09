class FieldConf

  class << self
    def from(config, index)
      name = config['name']
      type = config['type']
      default = config['default']
      model = config['model']
      key = config['key']
      value = config['value']
      sources = config['sources']
      imports = config['imports']
      block = config['block']
      expression = config['expression']
      new(index, name, type, default, model, key, value, sources, imports, block, expression)
    end
  end

  attr_reader :index,
              :name,
              :store_name,
              :display_name,
              :type,
              :default,
              :model,
              :key,
              :value,
              :sources,
              :imports,
              :block,
              :expression

  def initialize(index, name, type, default, model, key, value, sources, imports, block, expression)
    if index.nil?
      raise ArgumentError, 'index is required on field'
    end
    @index = index
    @name, @store_name, @display_name = parse_names(name)
    @type, @required, @virtual, @hidden, @readonly, @transient, @increment = parse_type_and_modifiers(type)
    @default = default.to_s unless default.nil?
    @model = model.to_s unless model.nil?
    @key = key.to_s unless key.nil?
    @value = value.to_s unless value.nil?
    @sources = parse_sources(sources)
    @imports = parse_imports(imports)
    @block = block.to_s unless block.nil?
    @expression = expression.to_s unless expression.nil?
  end

  def required?
    @required
  end

  def virtual?
    @virtual
  end

  def hidden?
    @hidden
  end

  def readonly?
    @readonly
  end

  def transient?
    @transient
  end

  def increment?
    @increment
  end

  # Returns whether the field should be stored in MongoDB
  #
  # @return [Boolean] true if the field should be stored in MongoDB,
  #         false otherwise
  def store_field?
    not virtual? and not readonly? and not transient?
  end

  # Returns whether the field should be displayed
  #
  # @return [Boolean] true if the field should be displayed,
  #         false otherwise
  def display_field?
    not hidden?
  end

  # Returns the field name in screaming snake case
  #
  # @return [String] the field name in screaming snake case
  def screaming_snake_case_name
    @name.gsub(/[A-Z]/) { |match| "_#{match}" }.upcase
  end

  # Returns the name of the store name const of the field
  #
  # @return [String] the name of the store name const of the field
  def store_name_const_name
    "STORE_NAME_#{screaming_snake_case_name}"
  end

  # Returns the name of the display name const of the field
  #
  # @return [String] the name of the display name const of the field
  def display_name_const_name
    "DISPLAY_NAME_#{screaming_snake_case_name}"
  end

  # Returns the name of the field index const of the field
  #
  # @return [String] the name of the field index const of the field
  def field_index_const_name
    "FIELD_INDEX_#{screaming_snake_case_name}"
  end

  # Returns the getter method name of the field
  #
  # @return [String] the getter method name of the field
  def getter_name
    if @type == 'boolean' and required?
      "is#{camel_case_name}"
    else
      "get#{camel_case_name}"
    end
  end

  # Returns the setter method name of the field
  #
  # @return [String] the setter method name of the field
  def setter_name
    "set#{camel_case_name}"
  end

  # Returns the field name in camel case
  #
  # @return [String] the field name in camel case
  def camel_case_name
    @name[0].upcase << @name[1..]
  end

  def has_modifier?(modifier)
    @modifiers.include?(modifier)
  end

  def has_children?
    @type == 'object' or @type == 'map'
  end

  private
  def parse_names(name_value)
    if name_value.nil?
      raise ArgumentError, 'name is required on field'
    end
    name, store_name, display_name = name_value.to_s.split(/\s+/)
    if store_name.nil?
      store_name = name
    end
    if display_name.nil?
      display_name = name
    end
    [name, store_name, display_name]
  end

  def parse_type_and_modifiers(type_and_modifiers)
    if type_and_modifiers.nil?
      raise ArgumentError, 'type is required on field'
    end
    modifiers = type_and_modifiers.to_s.split(/\s+/)
    type = modifiers[0]
    # modifiers default values
    required = false
    virtual = false
    hidden = false
    readonly = false
    transient = false
    increment = false
    modifiers[1..].each do |modifier|
      case modifier
      when 'required'
        required = true
      when 'virtual'
        virtual = true
      when 'hidden'
        hidden = true
      when 'readonly'
        readonly = true
      when 'transient'
        transient = true
      when 'increment', 'increment-1'
        increment = true
      end
    end
    @modifiers = modifiers[1..]
    [type, required, virtual, hidden, readonly, transient, increment]
  end

  def parse_sources(sources)
    parse_string_array(sources, 'sources')
  end

  def parse_string_array(array, name)
    if array.nil?
      return []
    end
    unless array.is_a?(Array)
      raise ArgumentError, "#{name} must be an array on field"
    end
    array.map { |item| item.to_s }
  end

  def parse_imports(imports)
    parse_string_array(imports, 'imports')
  end

end
